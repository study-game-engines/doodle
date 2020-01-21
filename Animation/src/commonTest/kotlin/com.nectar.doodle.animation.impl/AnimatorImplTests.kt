package com.nectar.doodle.animation.impl

import com.nectar.doodle.animation.Animation
import com.nectar.doodle.animation.Animator.Listener
import com.nectar.doodle.animation.fixedTimeLinear
import com.nectar.doodle.animation.fixedTimeLinearM
import com.nectar.doodle.scheduler.AnimationScheduler
import com.nectar.doodle.scheduler.Task
import com.nectar.doodle.time.Timer
import com.nectar.measured.units.Measure
import com.nectar.measured.units.Time
import com.nectar.measured.units.hours
import com.nectar.measured.units.milliseconds
import com.nectar.measured.units.seconds
import com.nectar.measured.units.times
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.expect

/**
 * Created by Nicholas Eddy on 12/15/19.
 */
class AnimatorImplTests {
    private class MonotonicTimer: Timer {
        override var now = 100 * milliseconds
            get() = field.also { field += 2 * milliseconds }
            private set
    }

    private class ImmediateAnimationScheduler: AnimationScheduler {
        override fun onNextFrame(job: (Measure<Time>) -> Unit) = object: Task {
            override val completed = true

            override fun cancel() {}
        }.also {
            job(2 * milliseconds)
        }
    }

    private class ManualAnimationScheduler: AnimationScheduler {
        private inner class ManualTask(private val job: (Measure<Time>) -> Unit): Task {
            override var completed = false

            init {
                activeTasks += this
            }

            fun complete() {
                completed = true
                job(1 * milliseconds)
            }

            override fun cancel() {
                activeTasks -= this
            }
        }

        private val activeTasks = mutableSetOf<ManualTask>()

        override fun onNextFrame(job: (Measure<Time>) -> Unit) = ManualTask(job)

        fun runOutstandingTasks() {
            val tasks = activeTasks.toSet()
            activeTasks.clear()

            tasks.forEach {
                it.complete()
            }
        }
    }

    @Test fun `animates number property`() {
        val timer              = MonotonicTimer()
        val animationScheduler = ImmediateAnimationScheduler()
        val animate            = AnimatorImpl(timer, animationScheduler)
        val listener           = mockk<Listener>(relaxed = true)

        val outputs = mutableListOf<Float>()

        animate.listeners += listener

        val animation = (animate (0f to 1f) using fixedTimeLinear(3 * milliseconds)) {
            outputs += it
        }

        expect(listOf(0f, 2/3f, 1f)) { outputs }

        verify(exactly = 2) { listener.changed  (animate, setOf(animation)) }
        verify(exactly = 1) { listener.completed(animate, setOf(animation)) }
    }

    @Test fun `animates second batch of number properties`() {
        val timer              = MonotonicTimer()
        val animationScheduler = ImmediateAnimationScheduler()
        val animate            = AnimatorImpl(timer, animationScheduler)
        val listener           = mockk<Listener>(relaxed = true)

        var outputs = mutableListOf<Float>()

        animate.listeners += listener

        var animation = (animate (0f to 1f) using fixedTimeLinear(3 * milliseconds)) {
            outputs.plusAssign(it)
        }

        expect(listOf(0f, 2/3f, 1f)) { outputs }

        verify(exactly = 2) { listener.changed  (animate, setOf(animation)) }
        verify(exactly = 1) { listener.completed(animate, setOf(animation)) }

        outputs = mutableListOf()

        animation = (animate (0f to 1f) using fixedTimeLinear(3 * milliseconds)) {
            outputs.plusAssign(it)
        }

        expect(listOf(0f, 2/3f, 1f)) { outputs }

        verify(exactly = 2) { listener.changed  (animate, setOf(animation)) }
        verify(exactly = 1) { listener.completed(animate, setOf(animation)) }
    }

    @Test fun `animates measure property`() {
        val timer              = MonotonicTimer()
        val animationScheduler = ImmediateAnimationScheduler()
        val animate            = AnimatorImpl(timer, animationScheduler)
        val listener           = mockk<Listener>(relaxed = true)

        val outputs = mutableListOf<Measure<Time>>()

        animate.listeners += listener

        val animation = (animate (0 * seconds to 1 * hours) using fixedTimeLinearM(3 * milliseconds)) {
            outputs += it
        }

        expect(listOf(0 * milliseconds, 2 * hours / 3, 1000 * milliseconds * 3600)) { outputs }

        verify(exactly = 2) { listener.changed  (animate, setOf(animation)) }
        verify(exactly = 1) { listener.completed(animate, setOf(animation)) }
    }

    @Test fun `cancels number animation`() {
        val timer              = MonotonicTimer()
        val animationScheduler = ManualAnimationScheduler()
        val animate            = AnimatorImpl(timer, animationScheduler)
        val listener           = mockk<Listener>(relaxed = true)

        val outputs1 = mutableListOf<Float>()
        val outputs2 = mutableListOf<Float>()

        animate.listeners += listener

        val animation1 = (animate (0f to 1f) using fixedTimeLinear(3 * milliseconds)) { outputs1 += it }
        val animation2 = (animate (0f to 1f) using fixedTimeLinear(3 * milliseconds)) { outputs2 += it }

        animation1.cancel()

        animationScheduler.runOutstandingTasks()
        animationScheduler.runOutstandingTasks()

        expect(true) { outputs1.isEmpty() }
        expect(listOf(0f, 2/3f)) { outputs2 } // Only the first 2 frames ticks, which begins animation2 and notifies with the first value

        verify(exactly = 1) { listener.changed  (animate, setOf(animation2)         ) }
        verify(exactly = 0) { listener.changed  (animate, match { animation1 in it }) }
        verify(exactly = 0) { listener.completed(animate, any()                     ) }
        verify(exactly = 1) { listener.cancelled(animate, setOf(animation1)         ) }
    }

    @Test fun `animation block cancels nested`() {
        val timer              = MonotonicTimer()
        val animationScheduler = ManualAnimationScheduler()
        val animate            = AnimatorImpl(timer, animationScheduler)
        val listener           = mockk<Listener>(relaxed = true)

        val outputs1 = mutableListOf<Float>()
        val outputs2 = mutableListOf<Float>()

        animate.listeners += listener

        var animation1 = null as Animation?
        var animation2 = null as Animation?

        val topLevel = animate {
            animation1 = (0f to 1f using fixedTimeLinear(3 * milliseconds)) { outputs1 += it }

            animate {
                animation2 = (0f to 1f using fixedTimeLinear(3 * milliseconds)) { outputs2 += it }
            }
        }

        topLevel.cancel()

        animationScheduler.runOutstandingTasks()
        animationScheduler.runOutstandingTasks()

        expect(true) { outputs1.isEmpty() }
        expect(true) { outputs2.isEmpty() }

        verify(exactly = 0) { listener.changed  (animate, any  (                          )) }
        verify(exactly = 0) { listener.completed(animate, any  (                          )) }
        verify(exactly = 1) { listener.cancelled(animate, setOf(animation1!!, animation2!!)) }
    }

    @Test fun `cancels number animation group`() {
        val timer              = MonotonicTimer()
        val animationScheduler = ManualAnimationScheduler()
        val animate            = AnimatorImpl(timer, animationScheduler)
        val listener           = mockk<Listener>(relaxed = true)

        val outputs1 = mutableListOf<Float>()
        val outputs2 = mutableListOf<Float>()

        animate.listeners += listener

        val animations = animate {
            (0f to 1f using fixedTimeLinear(3 * milliseconds)) { outputs1 += it }
            (0f to 1f using fixedTimeLinear(3 * milliseconds)) { outputs2 += it }
        }

        animations.cancel()

        animationScheduler.runOutstandingTasks()

        expect(true) { outputs1.isEmpty() }
        expect(true) { outputs2.isEmpty() }

        verify(exactly = 0) { listener.changed  (animate, any()) }
        verify(exactly = 0) { listener.completed(animate, any()) }
        verify(exactly = 1) { listener.cancelled(animate, match { it.size == 2 }) }
    }
}
package dev.game.spacechaos.engine.time;

import dev.game.spacechaos.engine.time.listener.CooldownTimerFinishedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 07.03.2017.
 */
public class CooldownTimer extends GameTimer {

    protected long interval = 0;
    protected long endTime = 0;

    // list with listeners
    protected List<CooldownTimerFinishedListener> timerFinishedListenerList = new ArrayList<>();

    protected boolean hasFinished = false;

    public CooldownTimer(long interval) {
        this.interval = interval;
    }

    @Override
    protected void afterStart(GameTime time) {
        // calculate end time
        endTime = startTime + interval;
    }

    @Override
    protected void afterStop(GameTime time) {
        //
    }

    @Override
    protected void afterUpdate(GameTime time) {
        if (time.getTime() > this.endTime) {
            // stop timer
            this.stop(time);

            this.hasFinished = true;

            // call listeners
            onEndTimeReached(time);
        }
    }

    public long getEndTime() {
        return this.startTime + interval;
    }

    public long getRemainingTime() {
        long remainingTime = this.interval - this.elapsed;

        if (remainingTime < 0) {
            return 0;
        }

        return remainingTime;
    }

    public long getInterval() {
        return this.interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void reset(GameTime time) {
        this.startTime = time.getTime();
        this.elapsed = 0;
        this.hasFinished = false;
    }

    public void reset() {
        this.reset(GameTime.getInstance());
    }

    protected void onEndTimeReached(GameTime time) {
        this.timerFinishedListenerList.stream().forEach(listener -> {
            // call listener
            listener.onFinished(this.startTime, this.interval, time.getTime());
        });
    }

    public void setFinishedListener(CooldownTimerFinishedListener listener) {
        // clear list
        this.timerFinishedListenerList.clear();

        if (listener != null) {
            this.timerFinishedListenerList.add(listener);
        }
    }

    public void removeAllFinishedListeners() {
        // clear listener list
        this.timerFinishedListenerList.clear();
    }

}

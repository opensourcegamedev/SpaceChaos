package dev.game.spacechaos.engine.time;

/**
 * Created by Justin on 07.03.2017.
 */
public class GameTimer {

    //start time of timer
    protected long startTime = 0;

    protected volatile long elapsed = 0;

    protected boolean isRunning = false;

    /**
    * default constructor
    */
    public GameTimer () {
        //
    }

    /**
    * start timer
     *
     * @param time current game time
    */
    public void start (GameTime time) {
        if (isRunning) {
            throw new IllegalStateException("Timer is already running, stop timer first.");
        }

        //set start time
        this.startTime = time.getTime();

        this.elapsed = 0;

        //set running flag
        this.isRunning = true;

        afterStart(time);
    }

    /**
     * start timer
     */
    public void start () {
        this.start(GameTime.getInstance());
    }

    protected void afterStart (GameTime time) {
        //
    }

    /**
    * stop timer
     *
     * @param time current game time
    */
    public void stop (GameTime time) {
        if (!isRunning) {
            throw new IllegalStateException("Cannot stop timer, because timer isnt running.");
        }

        //update first to calculate elapsed time
        this.update(time);

        //stop timer
        this.isRunning = false;

        afterStop(time);
    }

    /**
     * stop timer
     */
    public void stop () {
        this.stop(GameTime.getInstance());
    }

    protected void afterStop (GameTime time) {
        //
    }

    public void update (GameTime time) {
        //check if timer isnt running
        if (!isRunning) {
            //we dont need to update timer
            return;
        }

        //get current timestamp in milliseconds
        long now = time.getTime();

        //calculate elapsed time
        this.elapsed = now - this.startTime;

        this.afterUpdate(time);
    }

    protected void afterUpdate (GameTime time) {
        //
    }

    public void update () {
        this.update(GameTime.getInstance());
    }

    public long getStartTime () {
        return this.startTime;
    }

    public long getElapsedTime () {
        return this.elapsed;
    }

    public boolean isRunning () {
        return this.isRunning;
    }

}

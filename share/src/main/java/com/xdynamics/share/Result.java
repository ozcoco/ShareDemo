package com.xdynamics.share;

import com.facebook.share.Sharer;

public class Result extends Sharer.Result {

    /**
     * Constructor.
     *
     * @param postId the resulting post id.
     */
    public Result(String postId) {
        super(postId);
    }
}

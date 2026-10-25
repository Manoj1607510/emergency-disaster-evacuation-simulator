package com.evacuation.rescue;

import com.evacuation.model.RescueRequest;

import java.util.Comparator;
import java.util.PriorityQueue;

public class RescueManager {

    private final PriorityQueue<RescueRequest> rescueQueue;

    public RescueManager() {
        rescueQueue = new PriorityQueue<>(
                Comparator.comparingInt(
                        (RescueRequest request) ->
                                request.getPriority().getLevel()
                ).reversed()
        );
    }

    public void addRequest(RescueRequest request) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "Rescue request cannot be null"
            );
        }

        rescueQueue.offer(request);
    }

    public RescueRequest getNextRequest() {
        return rescueQueue.peek();
    }

    public RescueRequest processNextRequest() {
        return rescueQueue.poll();
    }

    public int getQueueSize() {
        return rescueQueue.size();
    }

    public boolean isEmpty() {
        return rescueQueue.isEmpty();
    }

    public void clear() {
        rescueQueue.clear();
    }
}
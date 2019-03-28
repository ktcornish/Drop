package com.steamybeans.drop.firebase;

import android.content.Intent;
import android.os.Bundle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AchievementsTest extends Achievements {


    public AchievementsTest(Notifications notifications) {
        super(notifications);
    }

    @Test
    @DisplayName("Upvotes received achievement")
    void noneUpvotes() {
        Intent intent = new Intent();
        Long zero = new Long(0);
        Achievements achievements = new Achievements(new Notifications(this));
        achievements.upvotesReceivedAchievement(zero, intent);
        Bundle extras = getIntent().getExtras();
        assertEquals("upvotes_received_none", extras.getString("downVotesGivenAchievement"));
    }
}
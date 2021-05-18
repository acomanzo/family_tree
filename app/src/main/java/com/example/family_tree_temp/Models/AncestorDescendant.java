package com.example.family_tree_temp.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(
        entity = FamilyMember.class,
        parentColumns = "familyMemberId",
        childColumns = "ancestorId",
        onDelete = ForeignKey.CASCADE
), @ForeignKey(
        entity = FamilyMember.class,
        parentColumns = "familyMemberId",
        childColumns = "descendantId",
        onDelete = ForeignKey.CASCADE
)})
public class AncestorDescendant {

    @PrimaryKey(autoGenerate = true)
    private int ancestorDescendantId;

    @NonNull
    private final int ancestorId;

    @NonNull
    private final int descendantId;

    @NonNull
    private final int depth;

    @NonNull
    private int serverId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    public AncestorDescendant(int ancestorId, int descendantId, int depth) {
        this(ancestorId, descendantId, depth, -1, "", "");
    }

    @Ignore
    public AncestorDescendant(int ancestorId, int descendantId, int depth, int serverId, @NonNull String createdAt, @NonNull String updatedAt) {
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
        this.depth = depth;
        this.serverId = serverId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public AncestorDescendant(int ancestorDescendantId, int ancestorId, int descendantId, int depth, int serverId, @NonNull String createdAt, @NonNull String updatedAt) {
        this.ancestorDescendantId = ancestorDescendantId;
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
        this.depth = depth;
        this.serverId = serverId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getAncestorDescendantId() {
        return ancestorDescendantId;
    }

    public int getAncestorId() {
        return ancestorId;
    }

    public int getDescendantId() {
        return descendantId;
    }

    public int getDepth() {
        return depth;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @NonNull
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

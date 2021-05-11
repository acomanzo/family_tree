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
    private int ancestorId;

    @NonNull
    private int descendantId;

    @NonNull
    private int depth;

    private int serverId;

    @NonNull
    private String createdAt;

    @NonNull
    private String updatedAt;

    @Ignore
    public AncestorDescendant(@NonNull int ancestorId, @NonNull int descendantId, @NonNull int depth) {
        this(ancestorId, descendantId, depth, -1);
    }

    @Ignore
    public AncestorDescendant(@NonNull int ancestorId, @NonNull int descendantId, @NonNull int depth, int serverId) {
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
        this.depth = depth;
        this.serverId = serverId;
    }

    public AncestorDescendant(int ancestorDescendantId, @NonNull int ancestorId, @NonNull int descendantId, @NonNull int depth, int serverId, @NonNull String createdAt, @NonNull String updatedAt) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

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

    @Ignore
    public AncestorDescendant(@NonNull int ancestorId, @NonNull int descendantId, @NonNull int depth) {
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
        this.depth = depth;
    }

    public AncestorDescendant(int ancestorDescendantId, @NonNull int ancestorId, @NonNull int descendantId, @NonNull int depth) {
        this.ancestorDescendantId = ancestorDescendantId;
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
        this.depth = depth;
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
}

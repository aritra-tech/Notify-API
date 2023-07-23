package com.aritra.data.database

import com.aritra.data.model.Notes
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo


private val client = KMongo.createClient().coroutine
private val db = client.getDatabase("NotesDatabase")

private val note = db.getCollection<Notes>()

suspend fun getAllNotes(): List<Notes> {
    return note.find().toList()
}

suspend fun getNotesForId(id: String): Notes? {
    return note.findOneById(id)
}

suspend fun addOrUpdateNote(notes: Notes): Boolean {
    val noteExists = note.findOneById(notes.id) != null
    return if (noteExists) {
        note.updateOneById(notes.id, notes).wasAcknowledged()
    } else {
        notes.id = ObjectId().toString()
        note.insertOne(notes).wasAcknowledged()
    }
}

suspend fun deleteNote(noteId: String): Boolean {
    val notesList = note.findOneById(Notes::id eq noteId)
    notesList?.let {
        return note.deleteOneById(it.id).wasAcknowledged()
    } ?: return false
}
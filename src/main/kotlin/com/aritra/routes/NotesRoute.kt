package com.aritra.routes

import com.aritra.data.addOrUpdateNote
import com.aritra.data.deleteNote
import com.aritra.data.getAllNotes
import com.aritra.data.getNotesForId
import com.aritra.data.model.Notes
import com.aritra.data.requests.DeleteNoteRequest
import com.aritra.data.requests.NotesRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.noteRoutes() {

    route("/get-all-notes") {
        get {
            val listOfNote: List<Notes> = getAllNotes()
            return@get if (listOfNote.isEmpty()) {
                call.respond(HttpStatusCode.OK, "No Employees created")
            } else {
                call.respond(HttpStatusCode.OK, listOfNote)
            }
        }
    }

    route("/get-notes") {
        get {
            val noteId = call.receive<NotesRequest>().id
            val notes = getNotesForId(noteId)
            notes?.let {
                call.respond(
                    HttpStatusCode.OK,
                    it
                )
            } ?: call.respond(
                HttpStatusCode.OK,
                "There is no Note with this id"
            )
        }
    }

    route("/add-update-note") {
        post {
            val request = try {
                call.receive<Notes>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            if (addOrUpdateNote(request)) {
                call.respond(HttpStatusCode.OK,"Successfully Saved")
            } else {
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }

    route("/delete-note") {
        post {
            val request = try {
                call.receive<DeleteNoteRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            if (deleteNote(request.id)) {
                call.respond(HttpStatusCode.OK,"Successfully Deleted")
            }
            else {
                call.respond(HttpStatusCode.OK,"Note not found")
            }
        }
    }
}
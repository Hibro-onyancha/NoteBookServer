package com.example.noteBook.layers.domain.controller

import com.example.noteBook.layers.data.helpers.ApiResponse
import com.example.noteBook.layers.data.models.Notes
import com.example.noteBook.layers.domain.service.NotesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/notes")
class NotesController(
    private val notesService: NotesService
) {
    @PostMapping
    suspend fun insertNewNotes(@RequestBody notes: List<Notes>): ResponseEntity<ApiResponse> {
        val response = notesService.insertNote(notes)
        return if (response.isSuccess) ResponseEntity.ok().body(response) else ResponseEntity.badRequest()
            .body(response)
    }

    @PutMapping //http://localhost:8080/notes
    suspend fun updateNewNotes(@RequestBody notes: Notes): ResponseEntity<ApiResponse> {
        val response = notesService.updateNote(notes)
        return if (response.isSuccess) ResponseEntity.ok().body(response) else ResponseEntity.badRequest()
            .body(response)
    }

    @GetMapping("/{id}")
    suspend fun getSingleNote(@PathVariable id: String): ResponseEntity<Notes?> {
        val response = notesService.getNote(id)
        return if (response != null) ResponseEntity.ok().body(response) else ResponseEntity.notFound()
            .build()
    }

    @GetMapping("/filter")
    suspend fun getNotesDynamically(
        @RequestParam(required = false) field: String,
        @RequestParam(required = false) value: String,
        @RequestParam(defaultValue = "0") index: Int,
        @RequestParam(defaultValue = "15") size: Int
    ): ResponseEntity<List<Notes>> {
        val notes = notesService.getNotesDynamically(field, value, index, size)
        return ResponseEntity.ok().body(notes)
    }

}
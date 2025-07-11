package com.example.noteBook.layers.domain.service

import com.example.noteBook.layers.data.helpers.ApiResponse
import com.example.noteBook.layers.data.models.Notes
import com.example.noteBook.layers.data.repo.NotesRepository
import org.springframework.stereotype.Service

@Service
class NotesService(
    private val notesRepository: NotesRepository
) {
    suspend fun insertNote(notes: List<Notes>): ApiResponse {
        return notesRepository.insertNote(notes)
    }

    suspend fun updateNote(notes: Notes): ApiResponse {
        return notesRepository.updateNote(notes)
    }

    suspend fun getNote(id: String): Notes? {
        return notesRepository.getNote(id)
    }

    suspend fun getNotesDynamically(field: String, value: String, index: Int, size: Int): List<Notes> {
        return notesRepository.getNotesDynamically(field, value, index, size)
    }
}
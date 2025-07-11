package com.example.noteBook.layers.data.repo

import com.example.noteBook.layers.data.helpers.ApiResponse
import com.example.noteBook.layers.data.models.Notes

interface NotesRepository {
    suspend fun insertNote(notes: List<Notes>): ApiResponse
    suspend fun updateNote(notes: Notes): ApiResponse
    suspend fun getNote(id: String): Notes?
    suspend fun getNotesDynamically(field: String, value: String, index: Int, size: Int): List<Notes>
}
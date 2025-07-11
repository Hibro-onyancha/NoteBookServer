package com.example.noteBook.layers.domain.repoImpl

import com.example.noteBook.layers.data.helpers.ApiResponse
import com.example.noteBook.layers.data.models.Notes
import com.example.noteBook.layers.data.repo.NotesRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.set
import org.springframework.stereotype.Repository


@Repository
class NotesRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : NotesRepository {
    override suspend fun insertNote(notes: List<Notes>): ApiResponse {
        /*mongoTemplate.insertAll(notes)
        return ApiResponse(isSuccess = true, message = "Successfully added the notes")*/
        return try {
            mongoTemplate.insertAll(notes)
            ApiResponse(isSuccess = true, message = "Successfully added the notes")
        } catch (e: Exception) {
            println(e.message)
            ApiResponse(isSuccess = false, message = e.message ?: "something went wrong")
        }
    }

    override suspend fun updateNote(notes: Notes): ApiResponse {
        return try {
            val query = Query(Criteria.where("id").`is`(notes.id))
            val update = Update()
                .set(Notes::note, notes.note)
                .set(Notes::title, notes.title)
                .set(Notes::date, notes.date)
            val isUpdated = mongoTemplate.updateFirst(query, update, Notes::class.java)
            val isSuccessful = isUpdated.modifiedCount > 0
            ApiResponse(isSuccess = isSuccessful, message = "Successfully added the notes")
        } catch (e: Exception) {
            println(e.message)
            ApiResponse(isSuccess = false, message = e.message ?: "something went wrong")
        }
    }

    override suspend fun getNote(id: String): Notes? {
        return try {
            val query = Query(Criteria.where("id").`is`(id))
            val note = mongoTemplate.findOne(query, Notes::class.java)
            note
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    override suspend fun getNotesDynamically(field: String, value: String, index: Int, size: Int): List<Notes> {
        return try {
            val query = if (field != "") {//if the field is empty, we return all the notes
                Query(Criteria.where(field).`is`(value))
                    .skip(index.toLong())
                    .apply {
                        if (size > 0) limit(size)
                    }
            } else {
                Query()
                    .skip(index.toLong())
                    .apply {
                        if (size > 0) limit(size)
                    }
            }
            val notes = mongoTemplate.find(query, Notes::class.java)
            notes
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }
    }
}
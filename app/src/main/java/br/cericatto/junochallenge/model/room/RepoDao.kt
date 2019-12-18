package br.cericatto.junochallenge.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import br.cericatto.junochallenge.model.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: MutableList<Repo>): List<Long>

    @Query("SELECT * FROM Repo")
    fun getAll(): LiveData<MutableList<Repo>>

    @Query("DELETE FROM Repo")
    fun deleteAll()

    @Update
    fun update(repo: Repo)
}
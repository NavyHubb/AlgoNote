package com.ssafy.algonote.note.repository;

import com.ssafy.algonote.note.domain.Bookmark;
import java.util.List;
import java.util.Optional;

import com.ssafy.algonote.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByNoteIdAndMemberId(Long noteId, Long memberId);
    List<Bookmark> findAllByMemberId(Long memberId);
    void deleteAllByNote(Note note);

}

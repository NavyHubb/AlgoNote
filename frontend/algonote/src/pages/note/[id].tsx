import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import getNoteDetail from '@/apis/note-detailAxios'
import style from '@/pages/note/note.module.scss'

interface Member {
  memberId: number
  nickname: string
}

interface Problem {
  id: number
  title: string
  tier: number
  acceptUserCount: number
  averageTries: number
  tags: string[]
}

interface NoteData {
  noteId: number
  member: Member
  problem: Problem
  noteTitle: string
  content: string
  heartCnt: number
  hearted: boolean
  createdAt: string
  modifiedAt: string
}

const Note = () => {
  const router = useRouter()
  const { id } = router.query
  const [noteDetail, setNoteDetail] = useState<NoteData>()

  useEffect(() => {
    const fetchNoteDetail = async () => {
      const response = await getNoteDetail(id as string)
      if (response) {
        console.log('노트 상세보기 응답', response.data)
        setNoteDetail(response.data)
      }
    }

    fetchNoteDetail()
  }, [])

  return (
    <div className={style.frame}>
      <div>제목 : {noteDetail?.noteTitle}</div>
      <div>
        문제 정보:
        <div>
          {noteDetail?.problem.id}
          {noteDetail?.problem.title}
          티어{noteDetail?.problem.tier}
          시도횟수{noteDetail?.problem.averageTries}푼 사람
          {noteDetail?.problem.acceptUserCount}
        </div>
      </div>
      <div>
        태그:
        {noteDetail?.problem.tags.map((tag) => <div key={tag}>{tag}</div>)}
      </div>
      <div>
        작성자:
        {noteDetail?.member.nickname}
      </div>
      <div>
        내용:
        {noteDetail?.content}
      </div>
      <div>
        좋아요 수:
        {noteDetail?.heartCnt}
      </div>
      <div>{noteDetail?.hearted ? '좋아요 했음' : '좋아요 안했음'}</div>
      <div>작성일: {noteDetail?.createdAt}</div>
      <div>수정일: {noteDetail?.modifiedAt}</div>
    </div>
  )
}

export default Note
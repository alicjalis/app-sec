export interface PostComment {
    id: number,
    content: string,
    author: string,
    reactionScore: number,
    userReaction: number | null,
    contentUri: string,
    uploadDate: Date,
}
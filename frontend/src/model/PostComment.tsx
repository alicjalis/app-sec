export interface PostComment {
    id: number,
    content: string,
    author: string,
    reactionScore: number,
    userReaction: number,
    contentUri: string,
    uploadDate: Date,
    isDeleted: boolean,
}
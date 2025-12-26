export interface Post {
    id: number,
    title: string,
    author: string,
    reactionScore: number,
    userReaction: number | null,
    contentUri: string,
    uploadDate: Date,
}
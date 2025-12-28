import type {PostComment} from "./PostComment.tsx";

export interface Post {
    id: number,
    title: string,
    author: string,
    reactionScore: number,
    userReaction: number,
    contentUri: string,
    uploadDate: Date,
    comments: PostComment[],
    isDeleted: boolean,
}
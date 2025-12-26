import {Box} from "@mui/material";
import {useParams} from "react-router-dom";
import {PostComponent} from "../components/PostComponent.tsx";
import {useEffect, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {GetCookie} from "../cookie/GetCookie.tsx";

function PostPage(){
    const [post, setPost] = useState();
    const { id } = useParams<{ id: string }>();
    const cookie= GetCookie();

    useEffect(() => {
        fetch(
            REQUEST_PREFIX + 'posts/id/' + id + "?username=" + cookie.username,
            {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                }
            }
        ).then(response => {
            if (response.ok) {
                return response.json();
            }
        }).then(data => {
            setPost(data);
        })
    }, [cookie.username, id]);

    return (
        <Box>
            {post && (<PostComponent post={post} displayUsername={true} displayComments={true}/>)}
        </Box>
    )
}

export default PostPage;
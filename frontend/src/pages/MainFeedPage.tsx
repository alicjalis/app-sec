import {Box, CircularProgress, Container, Stack, Typography} from "@mui/material";
import {useCallback, useEffect, useRef, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import type {Post} from "../model/Post.tsx";
import {PostComponent} from "../components/PostComponent.tsx";
import {GetCookie} from "../cookie/GetCookie.tsx";
import { useSearchParams } from "react-router-dom";

function MainFeedPage() {
    const [posts, setPosts] = useState<Post[]>([]);
    const [searchParams] = useSearchParams();
    const searchQuery = searchParams.get("search");
    const cookie = GetCookie();

    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);

    const pageRef = useRef(0);
    const observerTarget = useRef(null);

    const loadPosts = useCallback(async (pageNumber: number, isNewSearch: boolean = false) => {
        if (loading) return;
        setLoading(true);

        try {
            let url = REQUEST_PREFIX + 'posts?page=' + pageNumber + '&size=10';

            if (searchQuery) {
                url += `&search=${encodeURIComponent(searchQuery)}`;
            }

            const response = await fetch(url, {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                    ...(cookie?.token && { 'Authorization': 'Bearer ' + cookie.token })
                }
            });

            if (!response.ok) throw new Error("Network response was not ok");

            const newPosts: Post[] = await response.json();

            if (newPosts.length === 0) {
                setHasMore(false);
            }

            setPosts(prev => {
                if (isNewSearch) {
                    return newPosts;
                }
                const existingIds = new Set(prev.map(p => p.id));

                const uniqueNewPosts = newPosts.filter((p: Post) => !existingIds.has(p.id));
                return [...prev, ...uniqueNewPosts];
            });

            pageRef.current = pageNumber;

        } catch (error) {
            console.error("Error loading posts:", error);
        } finally {
            setLoading(false);
        }
    }, [cookie.token, searchQuery]);


    useEffect(() => {
        setHasMore(true);
        pageRef.current = 0;
        loadPosts(0, true);
    }, [searchQuery]);


    useEffect(() => {
        const observer = new IntersectionObserver(
            entries => {
                if (entries[0].isIntersecting && hasMore && !loading) {
                    const nextPage = pageRef.current + 1;
                    loadPosts(nextPage, false);
                }
            },
            { threshold: 1.0 }
        );

        if (observerTarget.current) {
            observer.observe(observerTarget.current);
        }

        return () => {
            if (observerTarget.current) {
                observer.unobserve(observerTarget.current);
            }
        };
    }, [hasMore, loading, loadPosts]);

    return (
        <Box sx={{ bgcolor: 'background.default', minHeight: '100vh', py: 3 }}>
            <Container maxWidth="md">
                <Stack spacing={2}>
                    {posts.filter(post => !post.isDeleted).map((post) => (
                        <PostComponent key={post.id} post={post} displayUsername={true} displayComments={false}/>
                    ))}

                    <Box ref={observerTarget} sx={{ width: '100%', height: 20, display: 'flex', justifyContent: 'center', mt: 2, minHeight: '50px' }}>
                        {loading && <CircularProgress />}
                        {!hasMore && posts.length > 0 && <Typography variant="caption">There is no more. Go touch grass!</Typography>}
                        {!hasMore && posts.length === 0 && <Typography variant="caption">No posts found.</Typography>}
                    </Box>
                </Stack>
            </Container>
        </Box>
    );
}

export default MainFeedPage;
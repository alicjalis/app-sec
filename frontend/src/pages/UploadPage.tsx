import {
    Box,
    Button,
    TextField,
    Typography,
    Paper,
    Stack,
    Container,
    CircularProgress,
    IconButton
} from '@mui/material';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import CloseIcon from '@mui/icons-material/Close';
import SendIcon from '@mui/icons-material/Send';
import {useEffect, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {GetCookie} from "../cookie/GetCookie.tsx";
import {useNavigate} from "react-router-dom";

function UploadPage() {
    const [loggedIn, setLoggedIn] = useState(false);
    const cookie = GetCookie();
    const navigate = useNavigate();
    const [title, setTitle] = useState("");
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [previewUrl, setPreviewUrl] = useState<string | null>(null);
    const [uploading, setUploading] = useState(false);
    const [userId, setUserId] = useState<number | null>(null);

    useEffect(() => {
        if (!cookie.token) return;

        fetch(REQUEST_PREFIX + 'auth/whoami', {
            method: "GET",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + cookie.token
            }
        })
            .then(async response => {
                if (response.ok) {
                    setLoggedIn(true);
                    return response.json();
                }
            }).then(data => {
                console.log(data);
                setUserId(data.id);
        })

    }, [cookie.token]);

    const handleFileSelect = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files || event.target.files.length === 0) return;

        const file = event.target.files[0];
        setSelectedFile(file);

        // Create a local URL just for preview
        const objectUrl = URL.createObjectURL(file);
        setPreviewUrl(objectUrl);
    };

    // 2. Clear Selection
    const handleClear = () => {
        setSelectedFile(null);
        setPreviewUrl(null);
    };

    const handleSubmit = async () => {
        if (!selectedFile || !title) {
            alert("Please provide both a title and an image");
            return;
        }

        setUploading(true);

        try {
            const formData = new FormData();
            formData.append('file', selectedFile);

            const imageResponse = await fetch(REQUEST_PREFIX + 'images/upload', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + cookie.token,
                },
                body: formData,
            });

            if (!imageResponse.ok) throw new Error("Image upload failed");

            const uploadedImageUrl = await imageResponse.text();
            console.log("Image uploaded:", uploadedImageUrl);

            const postResponse = await fetch(REQUEST_PREFIX + 'posts/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + cookie.token,
                },
                body: JSON.stringify({
                    title: title,
                    contentUri: uploadedImageUrl,
                    userId: userId,
                })
            });

            if (postResponse.ok) {
                console.log("Post created successfully!");
                navigate("/");
            } else {
                throw new Error("Failed to save post details");
            }

        } catch (error) {
            console.error("Upload Error:", error);
            alert("Something went wrong during upload.");
        } finally {
            setUploading(false);
        }
    };

    return (
        <Box>
            {!loggedIn ? (
                <Typography>Login to upload</Typography>
            ):(
                <Container maxWidth="sm" sx={{ py: 8 }}>
                    <Paper elevation={3} sx={{ p: 4, borderRadius: 3 }}>
                        <Typography variant="h5" fontWeight="bold" gutterBottom align="center">
                            Create a New Post
                        </Typography>

                        <Stack spacing={3} sx={{ mt: 3 }}>

                            {/* TITLE INPUT */}
                            <TextField
                                label="Post Title"
                                variant="outlined"
                                fullWidth
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                                placeholder="Give your post a catchy title..."
                                disabled={uploading}
                            />

                            {/* IMAGE UPLOAD AREA */}
                            <Box
                                sx={{
                                    border: '2px dashed',
                                    borderColor: 'divider',
                                    borderRadius: 2,
                                    p: 1,
                                    minHeight: 250,
                                    display: 'flex',
                                    flexDirection: 'column',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    bgcolor: 'background.default',
                                    position: 'relative',
                                    overflow: 'hidden'
                                }}
                            >
                                {previewUrl ? (
                                    <>
                                        {/* Image Preview */}
                                        {selectedFile?.type.startsWith('video/') ? (
                                            <Box
                                                component="video"
                                                src={previewUrl}
                                                controls
                                                sx={{ width: '100%', height: '100%', objectFit: 'contain', maxHeight: 300 }}
                                            />
                                        ) : (
                                            <Box
                                                component="img"
                                                src={previewUrl}
                                                alt="Preview"
                                                sx={{ width: '100%', height: '100%', objectFit: 'contain', maxHeight: 300 }}
                                            />
                                        )}
                                        {/* Close Button */}
                                        <IconButton
                                            onClick={handleClear}
                                            sx={{ position: 'absolute', top: 8, right: 8, bgcolor: 'rgba(0,0,0,0.6)', color: 'white', '&:hover': { bgcolor: 'rgba(0,0,0,0.8)' } }}
                                        >
                                            <CloseIcon />
                                        </IconButton>
                                    </>
                                ) : (
                                    <>
                                        {/* Upload Placeholder */}
                                        <CloudUploadIcon sx={{ fontSize: 60, color: 'text.secondary', mb: 2 }} />
                                        <Typography color="text.secondary">
                                            Drag and drop or click to upload
                                        </Typography>

                                        {/* Hidden Input + Label Button */}
                                        <Button
                                            variant="outlined"
                                            component="label"
                                            sx={{ mt: 2 }}
                                        >
                                            Choose File
                                            <input
                                                type="file"
                                                hidden
                                                accept="image/*,video/mp4"
                                                onChange={handleFileSelect}
                                            />
                                        </Button>
                                    </>
                                )}
                            </Box>

                            {/* SUBMIT BUTTON */}
                            <Button
                                variant="contained"
                                size="large"
                                onClick={handleSubmit}
                                disabled={uploading || !title || !selectedFile}
                                startIcon={uploading ? <CircularProgress size={20} color="inherit" /> : <SendIcon />}
                                sx={{ py: 1.5 }}
                            >
                                {uploading ? "Uploading..." : "Post"}
                            </Button>

                        </Stack>
                    </Paper>
                </Container>
            )}
        </Box>
    );
}

export default UploadPage;

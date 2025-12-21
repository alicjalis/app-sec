import {useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {handleApiError} from "../utils/errorHandler.ts";
import {Alert, Box, Typography} from "@mui/material";

function ConfirmEmailPage() {
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token");
    const [isConfirmationSuccessful, setIsConfirmationSuccessful] = useState(true);

    const [apiError, setApiError] = useState<string | null>(null);

    useEffect(() => {
        const confirmAccount = async () => {
            setApiError(null);

            try {
                const response = await fetch(REQUEST_PREFIX + 'auth/confirm-account?token=' + token, {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8',
                    },
                });

                console.log(response);

                if (!response.ok) {
                    const errorMessage = await handleApiError(response);
                    throw new Error(errorMessage);
                }

                const result = await response.json();
                setIsConfirmationSuccessful(result);
                console.log("Success:", result);

            } catch (error: any) {
                setApiError(error.message || "An unexpected error occurred.");
            }
        };

        confirmAccount();
    }, [token])

    return (
        <Box
            sx={{ display: 'flex', flexDirection: 'column', gap: 2, width: 300, margin: '50px auto' }}
        >
            {apiError && <Alert severity="error">{apiError}</Alert>}
            {isConfirmationSuccessful ? (
                <Box sx={{ p: 3, border: '2px solid green', borderRadius: 2, textAlign: 'center' }}>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        Confirmation Successful!
                    </Typography>
                    <Typography variant="h5" color="green" sx={{ mb: 1 }}>
                        (｡◕‿‿◕｡)
                    </Typography>
                    <Typography variant="body1">
                        Have fun.
                    </Typography>
                </Box>
            ) : (
                <Box sx={{ p: 3, border: '2px solid red', borderRadius: 2, textAlign: 'center' }}>
                    <Typography variant="h5" color="red" sx={{ mb: 1 }}>
                        Password Reset Failed!
                    </Typography>
                    <Typography variant="h5" color="red" sx={{ mb: 1 }}>
                        (ㅠ﹏ㅠ)
                    </Typography>
                    <Typography variant="body1">
                        So sad.
                    </Typography>
                </Box>
                )}
        </Box>
    )
}

export default ConfirmEmailPage;
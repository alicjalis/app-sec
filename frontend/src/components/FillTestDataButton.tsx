import React from 'react';
import { Button } from "@mui/material";
import type { UseFormSetValue, UseFormSetFocus } from "react-hook-form";

// move to types.ts maybe in the future
type FormData = {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
};

interface FillTestDataButtonProps {
    setValue: UseFormSetValue<FormData>;
    setFocus?: UseFormSetFocus<FormData>;
}

const FillTestDataButton: React.FC<FillTestDataButtonProps> = ({ setValue, setFocus }) => {

    const fillTestRegistrationData = () => {
        const testData = {
            username: 'TestUser123',
            email: 'test@mail.pl',
            password: 'Test123456@@',
            confirmPassword: 'Test123456@@',
        };

        setValue('username', testData.username, { shouldValidate: true });
        setValue('email', testData.email, { shouldValidate: true });
        setValue('password', testData.password, { shouldValidate: true });
        setValue('confirmPassword', testData.confirmPassword, { shouldValidate: true });

        setFocus?.('username');
    };

    return (
        <Button
            type="button"
            variant="outlined"
            color="secondary"
            onClick={fillTestRegistrationData}
            sx={{ mt: -1, mb: 1 }}
        >
            Fill the fields
        </Button>
    );
}

export default FillTestDataButton;
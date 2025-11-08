import {useEffect, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import type {Test} from "../model/Test.tsx";
import {Card, Container, Typography} from "@mui/material";

function TestPage() {

    const [users, setUsers] = useState<Test[]>([]);

    useEffect(() => {
        fetch(
            REQUEST_PREFIX + '/test/all',
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
            setUsers(data);
        })
    }, []);


    return (
        <Container>
            {users.map(user => (
                <Card
                    key={user.id}
                    sx={{
                        maxWidth: 300,
                        mx: "auto",
                        mt: 3,
                        padding: 1,
                        boxShadow: 3,
                        borderRadius: 2,
                        backgroundColor: "background.paper",
                    }}
                >
                    <Typography variant="body2">
                        <strong>ID:</strong> {user.id}
                    </Typography>
                    <Typography variant="body2">
                        <strong>Username:</strong> {user.username}
                    </Typography>
                    <Typography variant="body2">
                        <strong>Password:</strong> {user.password}
                    </Typography>
                </Card>
            ))}

        </Container>

    );
}

export default TestPage;
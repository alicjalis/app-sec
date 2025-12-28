export const handleApiError = async (response: Response): Promise<string> => {
    //if (response.status === 401 || response.status === 403) {
    //    return "Invalid username or password.";
    //}

    let message = `Error ${response.status}: ${response.statusText}`;

    try {
        const errorBody = await response.json();
        if (errorBody && errorBody.message) {
            message = errorBody.message;
        }
    } catch {
        // komentarz zeby nie krzyczal
    }

    return message;
};
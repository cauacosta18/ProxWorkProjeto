export const setTokens = (accessToken: string, refreshToken: string): void => {
    sessionStorage.setItem("accessToken", accessToken);
    sessionStorage.setItem("refreshToken", refreshToken);
};

export const getAccessToken = (): string | null => {
    return sessionStorage.getItem("accessToken");
};

export const getRefreshToken = (): string | null => {
    return sessionStorage.getItem("refreshToken");
};

export const clearTokens = (): void => {
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("refreshToken");
}

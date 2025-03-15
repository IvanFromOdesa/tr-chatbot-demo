import React from "react";

export const createStoreProvider = <T,>(StoreContext: React.Context<T | undefined>) => {
    interface StoreProviderProps extends React.PropsWithChildren {
        rootStore: T;
    }

    const StoreProvider: React.FC<StoreProviderProps> = ({ children, rootStore }) => {
        return (
            <StoreContext.Provider value={rootStore}>
                {children}
            </StoreContext.Provider>
        );
    };

    return StoreProvider;
};
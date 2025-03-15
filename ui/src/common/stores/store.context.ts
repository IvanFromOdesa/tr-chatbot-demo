import React, { useContext } from "react";

export const createStoreContext = <T,>() => {
    const StoreContext = React.createContext<T | undefined>(undefined);

    const useStoreContext = (): T => {
        const context = useContext(StoreContext);
        if (!context) {
            throw new Error("useStoreContext must be used within a StoreProvider");
        }
        return context;
    };

    return { StoreContext, useStoreContext };
};
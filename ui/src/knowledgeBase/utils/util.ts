export const getNestedError = (errors: any, path: string): string | undefined => {
    const parts = path.split('.');
    let current = errors;

    for (const part of parts) {
        if (current && typeof current === 'object' && part in current) {
            current = current[part];
        } else {
            return undefined;
        }
    }

    return typeof current === 'string' ? current : undefined;
};

export const getNestedTouched = (touched: any, path: string): boolean | undefined => {
    const parts = path.split('.');
    let current = touched;

    for (const part of parts) {
        if (current && typeof current === 'object' && part in current) {
            current = current[part];
        } else {
            return undefined;
        }
    }

    return typeof current === 'boolean' ? current : undefined;
};
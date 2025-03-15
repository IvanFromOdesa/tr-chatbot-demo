export const scrollTo = (targetId: string) => {
    document.getElementById(targetId)?.scrollIntoView({behavior: "smooth"});
}

export const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
}
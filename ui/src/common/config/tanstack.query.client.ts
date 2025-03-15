import {QueryClient} from "@tanstack/react-query";

export const TANSTACK_QUERY_CLIENT = new QueryClient();

// Query keys
export const GET_PDFS_KEY = "pdf";
export const GET_QAS_KEY = "qa";
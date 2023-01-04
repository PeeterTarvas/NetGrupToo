import axios, {AxiosResponse} from "axios";
import React, {useCallback} from "react";


const pathStart = "http://localhost:8000";

const get =
    async (item: any, path: string, setComponent: React.Dispatch<React.SetStateAction<any>>) => {
        let data = await getInfo(path, item).catch(error => {
            return error
        });
        setComponent(data.data);
        return data.data;
    }

async function getInfo(path: string, body: any) {
    return await
        axios.get(pathStart + path, {
            params: body
        }).then((response) => response);
}

async function postInfo(path: string, body: any): Promise<AxiosResponse<any>> {
    return await axios.post(pathStart + path, body).then(
        r => r
    );
}

async function putInfo(path: string, body:any): Promise<AxiosResponse<any>> {
    return await axios.put(pathStart + path, body).then(r => r);
}

export default {getInfo, postInfo, get, putInfo};
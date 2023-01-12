import axios, {AxiosResponse} from "axios";

/**
 *  These functions are for back-end front-end endpoint request sending.
 */
const pathStart = "http://localhost:8000";

/**
 * This is for sending get requests to back end.
 * @param item is request params for the get request.
 * @param path for the request.
 * @param setComponent this is for setState variable that will set the value of the useState function.
 */
const get =
    async (item: any, path: string, setComponent: React.Dispatch<React.SetStateAction<any>>) => {
        let data = await getInfo(path, item).catch(error => {
            return error
        });
        setComponent(data.data);
        return data.data;
    }

/**
 * This is the simpler version of get function which uses this function as well.
 * This is used in cases where get function won't work
 * @param path of the request
 * @param body is the request params
 */
async function getInfo(path: string, body: any) {
    return await
        axios.get(pathStart + path, {
            params: body
        }).then((response) => response);
}

/**
 * This function is for posting info to the back-end.
 * @param path of the post function in back-end.
 * @param body of the items we want to post.
 */
async function postInfo(path: string, body: any): Promise<AxiosResponse<any>> {
    return await axios.post(pathStart + path, body).then(
        r => r
    );
}

/**
 * This function is for put/update operation sending to the back-end.
 * @param path of the update function in the back-end.
 * @param body of what will be updated.
 */
async function putInfo(path: string, body:any): Promise<AxiosResponse<any>> {
    return await axios.put(pathStart + path, body).then(r => r);
}

export default {getInfo, postInfo, get, putInfo};
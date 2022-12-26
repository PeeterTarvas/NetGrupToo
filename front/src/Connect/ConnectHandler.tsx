import axios from "axios";
import UserDto from "../Dtos/UserDto";


const pathStart = "http://localhost:8000";


async function getInfo(path: string, body: any) {
    return await
        axios.get(pathStart + path, {
            params: body
        }).then((response) => response)
}

function postInfo(path: string, body: any) {
    axios.post(pathStart + path, body).then(
        r => console.log(r.status)
    )
}


export default {getInfo, postInfo};
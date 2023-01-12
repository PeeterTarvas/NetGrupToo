import React, {useEffect, useState} from "react";
import UserDto from "../../../Dtos/UserDto";
import connection from "../../../Connect/ConnectHandler";
import User from "../../Comps/User";
import Button from "../../Comps/Button";
import {useLocation, useNavigate} from "react-router-dom";

/**
 * This is the main admin page for admin where she/he can see all the users and their statuses on cost and number of items etc...
 */
const AdminPage = () => {

    const [users, setUsers] = useState<UserDto[]>();
    const navigate = useNavigate();
    const location = useLocation();
    
    const handleBeforeUnload = () => {
        window.location.reload();
    };

    /**
     * THis is for changing the maximum free items for the user.
     * @param user that the free items will be changed for.
     */
    const setMaximumItems = async (user: UserDto) => {
        let data = await connection.putInfo("/update", user);
        setUsers(data.data);
    }

    /**
     * On render this component sends a request to back-end requesting all the users
     */
    useEffect(() => {
        connection.get(null, "/users", setUsers)
        window.addEventListener('beforeunload', handleBeforeUnload);
        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload)
        };
    }, [])

    return (
        <div>
            <div onClick={() =>navigate(-1)}><Button id={"Back"} name={"Back"}/></div>
            <div className={"grid w-full h-full self-center justify-self-center"}>
            <div className={"grid grid-cols-4 grid-rows-3 gap-4 p-8 w-5/6 h-5/6 bg-teal-900 self-center justify-self-center"}>
            {
                users?.map(
                    (user) => (
                        user.role !== "ROLE_BUSINESS" ?(
                        <User
                        username={user.username}
                        email={user.email}
                        role={user.role}
                        maximumItems={user.maximumItems}
                        numberOfItems={user.numberOfItems}
                        cost={user.cost}
                        password={user.password}
                        referenceUserUsername={user.referenceUserUsername}
                        onChange={setMaximumItems}
                        itemsStatus={user.itemsStatus}
                        />
                        ) : ""
                    )
                )
            }
                {
                    users?.map(
                        (user) => (
                            user.role === "ROLE_BUSINESS" ?(
                                <User
                                    username={user.username}
                                    email={user.email}
                                    role={user.role}
                                    maximumItems={user.maximumItems}
                                    numberOfItems={user.numberOfItems}
                                    cost={user.cost}
                                    password={user.password}
                                    referenceUserUsername={user.referenceUserUsername}
                                    onChange={setMaximumItems}
                                    itemsStatus={user.itemsStatus}
                                />
                            ) : ""
                        )
                    )
                }
            </div>
            </div>
            </div>
    )

}
export default AdminPage;
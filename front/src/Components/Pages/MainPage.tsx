import UserDto from "../../Dtos/UserDto";
import React, {useCallback, useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import Button from "../Comps/Button";
import connection from "../../Connect/ConnectHandler";
import CatalogueDto from "../../Dtos/CatalogueDto";
import Catalogue from "../Comps/Catalogue";
import ProductDto from "../../Dtos/ProductDto";
import Product from "../Comps/Product";
import path from "path";



const MainPage = () => {

    let [catalogue, setCatalogue] = useState<CatalogueDto | undefined>(undefined);
    const location = useLocation();
    const navigate = useNavigate();
    const [user, setUser] = useState<UserDto|undefined>();
    const [childCatalogues, setChildCatalogues] = useState<CatalogueDto[]>()
    const [products, setProducts] = useState<ProductDto[]>()
    const [productSearchName, setProductSearchName] = useState("")

    const handleBeforeUnload = () => {
        window.location.reload();
    };

    const getInitialDirectory  = useCallback(async () => {
        let data = await connection.getInfo("/catalogue/init", {username: user?.username})
            .then((resp) => resp)
        setCatalogue(data.data)
        return data.data
    },[user])

    useEffect( () => {
        setUser(location.state.user);
        if (location.state.catalogue === undefined) {
            getInitialDirectory().then(
                (resp) => {
                    connection.get(null, '/catalogue/get/' + resp?.catalogueId + '/catalogues', setChildCatalogues);
                    connection.get(null, "/product/get/" + resp?.catalogueId + "/products", setProducts)
                }
            )
        } else {
            setCatalogue(location.state.catalogue);
            connection.get(null, '/catalogue/get/' + catalogue?.catalogueId + '/catalogues', setChildCatalogues);
            connection.get(null, "/product/get/" + catalogue?.catalogueId + "/products", setProducts)
        }
        window.addEventListener('beforeunload', handleBeforeUnload);
        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload)
        };

    }, [catalogue?.catalogueId, getInitialDirectory, location.state.catalogue, location.state.user])

    const onClickHandle = async (catalogueName: string, user: UserDto) => {
         await connection.getInfo("/catalogue/get", {
            catalogueName: catalogueName, username: user.username
        }).then((resp) => navigate("/main", {
            state: {
                user: user,
                catalogue: resp.data,
            }
        }))

    }


    return (
        <div className={"w-full overflow-x-hidden h-full self-center justify-self-center"}>

            <form className={"w-1/3 h-auto self-center"} onSubmit={
                (e) => {
                    const body = {
                        username: user?.username,
                        searchTerm: productSearchName
                    }
                    connection.get(body, "/product/get/like", setProducts)
                    e.preventDefault()
                }
            }>
                <label htmlFor="default-search"
                       className="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Search</label>
                <div className="relative">
                    <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                        <svg aria-hidden="true" className="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none"
                             stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                        </svg>
                    </div>
                    <input
                        value={productSearchName} onChange={(e) => setProductSearchName(e.target.value)}
                        type="search" id="default-search"
                        className="block w-full p-4 pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                        placeholder="Search for product by name"/>
                    <button type="submit"
                            className="text-white absolute right-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Search
                    </button>
                </div>
            </form>

            <div className={"h-1/5"}>
                <div>Username: {user?.username}</div>
                <div>Role: {user?.role}</div>
                <div>Directory: {catalogue?.catalogueName}</div>
                <div>
                {user?.role === "ROLE_BUSINESS" ? (<>Cost: {(user?.cost)} </>) : ""}
                </div>
                <div className={"grid grid-cols-3 gap-3 m-4"}>
                    <div onClick={() => navigate(-1)}>
                        <Button name={"Back"} id={"back"} />
                    </div>
                    <div
                        className={"w-full"}
                        onClick={() => {
                            navigate("/catalogueForm", {
                                state: { user: user, currentCatalogue: catalogue }
                            });
                        }}>
                        <Button name={"Create directory"} id={"createDir"} />
                    </div>
                    <div
                        className={"w-full"}
                        onClick={() => {
                            navigate("/productForm", {
                                state: { user: user, currentCatalogue: catalogue }
                            });
                        }}>
                        <Button name={"Create product"} id={"createDir"} />
                    </div>
                </div>
            </div>
            <div className={"grid grid-cols-4 grid-rows-4 gap-5 p-8 w-full h-full bg-teal-900 self-center justify-self-center"}>
                {childCatalogues?.map((catalogue) => (
                    <Catalogue
                        user={location.state.user}
                        onClickHandle={onClickHandle}
                        catalogueName={catalogue.catalogueName}
                    />
                ))}
                {products?.map((product) => (
                    <Product
                        name={product.name}
                        productOwner={user!.username}
                        condition={product.condition}
                        pictureLink={product.pictureLink}
                        serialNumber={product.serialNumber}
                        description={product.description}
                        amount={product.amount}

                    />
                ))}
            </div>
        </div>
    );


}
export default MainPage;
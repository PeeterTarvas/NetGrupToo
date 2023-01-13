import { Routes, Route } from 'react-router-dom';
import HomePage from "./Pages/HomePage";
import RegisterPage from "./Pages/RegisterPage";
import MainPage from "./Pages/MainPage";
import CatalogueForm from "./Pages/CatalogueForm";
import ProductForm from "./Pages/ProductForm";
import AdminPage from "./Pages/AdminPages/AdminPage";

/**
 * This class is for holding all the routs in the front-end.
 */
const Main = () => {

    return (
        <Routes >
            <Route path={"/*"}>
             <Route index element={<HomePage/>} />
             <Route path='register' element={<RegisterPage/>} />
             <Route path='main' element={<MainPage/>} />
             <Route path='catalogueForm' element={<CatalogueForm/>} />
             <Route path='productForm' element={<ProductForm/>} />
             <Route path='adminMain' element={<AdminPage/>}/>
            </Route>
        </Routes>
);
}
export default Main;
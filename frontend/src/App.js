import React from "react";
import { BrowserRouter } from "react-router-dom";
import Router from "./router/Routers";
import Navbar from "./components/navbar/Navbar";

const App = () => {
  return (
    <BrowserRouter>
      <Navbar />
      <Router />
    </BrowserRouter>
  );
};

export default App;

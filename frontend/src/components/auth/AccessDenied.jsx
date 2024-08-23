import React from "react";
import { Link } from "react-router-dom";

const AccessDenied = () => {
  return (
    <div className="flex justify-center items-center h-screen bg-gray-100">
      <div className="text-center">
        <h1 className="text-6xl font-bold text-red-600 mb-4">
          403: Access Denied
        </h1>
        <p className="text-lg text-gray-600 mb-8">
          You don't have permission to access this page. Please log in to
          continue.
        </p>
        <Link to="/login" className="text-blue-500 hover:underline">
          Go to Login
        </Link>
      </div>
    </div>
  );
};

export default AccessDenied;

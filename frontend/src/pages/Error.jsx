import React from "react";

const Error = () => {
  return (
    <div className="flex justify-center items-center h-screen bg-gray-100">
      <div className="text-center">
        <h1 className="text-6xl font-bold text-gray-800 mb-4">
          404: Page Not Found
        </h1>
        <p className="text-lg text-gray-600 mb-8">
          Oops! The page you are looking for doesn't exist or has been moved.
        </p>
        <a href="/" className="text-blue-500 hover:underline">
          Return to Home
        </a>
      </div>
    </div>
  );
};

export default Error;

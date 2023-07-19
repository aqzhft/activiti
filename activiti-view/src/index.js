import React from 'react';
import ReactDOM from 'react-dom/client';
import {
    createBrowserRouter,
    RouterProvider
} from "react-router-dom";
import BpmnView from './components/bpmn';
import HazardView from './components/hazard';
import HolidayView from './components/holiday';
import ProcessView from './components/process';
import TaskView from './components/task';
import './index.css';

const router = createBrowserRouter([
    {
        path: "/",
        element: <ProcessView />,
    },
    {
        path: "/view",
        element: <TaskView />,
    },
    {
        path: "/holiday",
        element: <HolidayView />,
    },
    {
        path: "/hazard",
        element: <HazardView />,
    },
    {
        path: "/bpmn",
        element: <BpmnView />,
    }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RouterProvider router={router} />
);

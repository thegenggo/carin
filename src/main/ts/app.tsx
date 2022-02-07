import { useEffect, useState } from 'react';
import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

interface Employee {
    id: number;
    firstName: number;
    lastName: string;
    description: string;
}

function App() {
    const [employees, setEmployees] = useState<Employee[]>([]);

    useEffect(() => {
        axios.get('/api/employees').then((response:any) => {
            console.log(response);
            setEmployees(response.data._embedded.employees);
        });
    });

    return (
        <EmployeeList employees={employees} />
    );
}

function EmployeeList(props: { employees: Employee[] }) {
    const organisms = props.employees.map((organism: Employee) => <Employee key={organism.id} organism={organism} />);
    return (
        <table>
            <tbody>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Description</th>
                </tr>
                {organisms}
            </tbody>
        </table>
    );
}

function Employee(props: { organism: Employee }) {
    return (
        <tr>
            <td>{props.organism.firstName}</td>
            <td>{props.organism.lastName}</td>
            <td>{props.organism.description}</td>
        </tr>
    );
}

ReactDOM.render(<App />, document.getElementById('react'));

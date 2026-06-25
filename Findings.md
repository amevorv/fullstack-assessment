useEffect null dependency: recommend to set setProjects as dependency in ProjectList
useEffect null dependency: recommend to set TaskBoard as dependency in TaskBoard

[nit]: file sturcture of react project

nit: readability in useEffect:
    getProjects().then(() => setProjects());

nit: omitting id and aria-labelled-by

try nd catch for request: return safe objects

 Backend
//cross origin (*) unrecommended
// Raw password? No hashed??
nit: sanitizing params
ux: projects loading (No circular loader or lazy loader)
nit: use tanstack to cache query
sql open for injection
No error handling for services
Not enough Observability incase
preventing duplicate users
sending password as part of the project response???
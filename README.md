# Notes

Simple back-end application to store notes.

## Install and run

1. Clone the project
2. `cd path/to/tlt-notes`
3. `docker-compose up --build`

## Usage

- Create a note:
  `POST` `/api/v1/notes/`

  With body:

    ```
    {
        "title": "string, not empty",
        "description": "string, not empty",
        "tags": [] // list of strings among: "IMPORTANT", "PERSONAL", "BUSINESS" (optional)
    }
    ```

- Update a note: `PUT` `/api/v1/notes/{id}` With same body as above
- Delete a note: `DELETE` `/api/v1/notes/{id}`
- Delete all notes: `DELETE` `/api/v1/notes/`
- Get a note: `GET` `/api/v1/notes/{id}`
- Get statistics for a note: `GET` `/api/v1/notes/{id}/stats`
- Get all notes:
    - First page: `GET` `/api/v1/notes/`
    - Specific page: `GET` `/api/v1/notes/?page={num_page}`
    - Specific number per page: `GET` `/api/v1/notes/?size={nb_results_per_page}`
    - Filtered by tag: `GET` `/api/v1/notes/?tag={tag}`
    - All combined: `GET` `/api/v1/notes/?page={num_page}&size={nb_results_per_page}&tag={tag}`

When a note is returned (create, update, get), it has this format:

```
{
    "id": "automatically_assigned_id",
    "title": "string",
    "createdAt": "creation_date", // ex: "2023-11-17T13:52:57.001Z"
    "description": "string",
    "tags": [] // list of strings among: "IMPORTANT", "PERSONAL", "BUSINESS"
}
```

When a note is returned in a list, it has this simplified format:

```
{
    "id": "automatically_assigned_id",
    "title": "string",
    "createdAt": "creation_date", // ex: "2023-11-17T13:52:57.001Z"
}
```

The statistics return a map of each word and its number of occurrences, ordered by occurrence number descending.

```
{
    "note": 2,
    "a": 1,
    "is": 1
}
```
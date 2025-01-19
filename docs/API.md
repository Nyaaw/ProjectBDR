# Project BDR API Documentation

This API provides endpoints for managing media content, lists, creators, and user interactions. It uses the HTTP protocol and serves HTML templates using Thymeleaf.

## Base Configuration

- **Server Port**: 8080
- **Static Files Directory**: `/public`
- **Template Directory**: `/templates`
- **Template Engine**: Thymeleaf with HTML mode

## Endpoints

### Home Page

- `GET /`

Display the home page with featured media content.

#### Response
- Renders `index.html` with the following data:
  - Five featured media items
  - List of genres
  - List of media types
  - List of video game types

#### Status Codes
- `200` (OK) - Page successfully rendered

### Explore Media

- `GET /explore`

Browse all available media content.

#### Response
- Renders `explore.html` with a list of all media items

#### Status Codes
- `200` (OK) - Page successfully rendered

### Media Operations

#### View Single Media

- `GET /media`

View details of a specific media item.

#### Request Parameters
- `id` (query parameter) - The ID of the media to display

#### Response
- Renders `media.html` with:
  - Media details
  - Available lists for the media
  - Comments
  - Creators

#### Status Codes
- `200` (OK) - Media found and displayed
- `400` (Bad Request) - Invalid media ID
- `404` (Not Found) - Media not found

#### Create New Media

- `POST /media`

Create a new media entry.

#### Request
Form data must include:
- `nom` - Media name
- `description` - Media description
- `typemedia` - Media type
- `genres` - List of genres
- `datesortie` - Release date (YYYY-MM-DD format)
- `createurs` - List of creator IDs
- `jeuvideotypes` - List of video game types (required if media type is "jeuvideo")

#### Status Codes
- `302` (Found) - Redirect to new media page
- `400` (Bad Request) - Invalid input data

### Insert Operations

#### View Insert Page

- `GET /insert`

Display the insert form for new content.

#### Response
- Renders the insert form template

#### Status Codes
- `200` (OK) - Form displayed successfully

#### Creator Management

- `POST /insert/createcreator` - Create a new creator
- `POST /insert/removecreator` - Remove an existing creator
- `POST /insert/addcreator` - Add a creator to media

#### Genre Management

- `POST /insert/addgenre` - Add a new genre
- `POST /insert/removegenre` - Remove a genre

#### Video Game Type Management

- `POST /insert/addvideogametype` - Add a video game type
- `POST /insert/removevideogametype` - Remove a video game type

### List Operations

#### View Single List

- `GET /list`

View a specific list's contents.

#### Response
- Renders list details and contained media

#### Status Codes
- `200` (OK) - List displayed successfully

#### View User Lists

- `GET /mylists`

View all lists belonging to the current user.

#### Response
- Renders a page with all user's lists

#### Create New List

- `POST /mylists/createlist`

Create a new media list.

### User Authentication

#### Login

- `GET /login`

Display login page.

#### Response
- Renders `login.html`

#### Create Account

- `GET /login_creation`

Display account creation page.

#### Response
- Renders `login_creation.html`

### Search Results

- `GET /result`

Display search results for media.

#### Response
- Renders `result.html` with matching media items

### Error Handling

The API includes built-in error handling for common scenarios:

- `404` - Page Not Found
  - Renders `error.html` with error code and message
- `400` - Bad Request
  - Renders `error.html` with error code and message

## Media Types

The API supports various media types defined in `TypeMedia.java`:
- Video Games (`jeuvideo`)
- Other media types as defined in the enum

## Creator Types

Creators can be of different types as defined in `TypeCreateur.java`:
- Individual (`personne`)
- Group (`groupe`)

## Data Models

The API uses the following main data models:
- `Media` - Represents media content
- `Liste` - Represents user-created lists
- `Createur` - Represents content creators
- `Commentaire` - Represents user comments
- `Utilisateur` - Represents user accounts





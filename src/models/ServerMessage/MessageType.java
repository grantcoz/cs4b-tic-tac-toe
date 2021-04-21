package models.ServerMessage;

public enum MessageType {
    AUTHENTICATION_ACKNOWLEDGED,
    AUTHENTICATION_REQUEST,
    AUTHENTICATION_RESULT,
    CHAT,
    CONNECTION,
    CONNECTION_SUCCESS,
    CONNECTION_FAILURE,
    CREATE_LOBBY,
    LOBBY_LIST,
    LOGIN,
    LOGIN_SUCCESS,
    LOGOUT,
    LOGOUT_SUCCESS,
    MOVE,
    PLAYER_PROPERTIES,
    REFRESH_SUCCESS,
    REFRESH_TOKEN,
    REGISTER,
    REGISTRATION_RESULT,
    REQUEST_PLAYER,
    UNKNOWN
}

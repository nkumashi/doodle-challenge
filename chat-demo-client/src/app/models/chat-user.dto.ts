import { JsonObject, JsonProperty } from 'json2typescript';

@JsonObject('ChatUserDTO')
export class ChatUserDTO {
    @JsonProperty('userId', String)
    userId = '';
    @JsonProperty('username', String)
    username = '';
}

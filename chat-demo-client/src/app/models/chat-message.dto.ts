import { JsonObject, JsonProperty } from 'json2typescript';
import { ChatUserDTO } from './chat-user.dto';

@JsonObject('ChatMessageDTO')
export class ChatMessageDTO {
    @JsonProperty('sentByUser', ChatUserDTO)
    sentByUser: ChatUserDTO;
    @JsonProperty('content', String)
    content = '';
    @JsonProperty('createdAt', String)
    createdAt = '';
}

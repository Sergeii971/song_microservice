package contracts

import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return new song metadata"

    request {
        url "/v1/songs"
        method POST()
        body(
                name: "test",
                artist: "test-artist",
                album: "test-album",
                length: "99999",
                resourceId: "999"
        )
        headers {
            contentType applicationJson()
        }
    }

    response {
        status CREATED()
        headers {
            contentType applicationJson()
        }
        body (
                name: "test",
                artist: "test-artist",
                album: "test-album",
                length: "99999",
                resourceId: "999"
        )
    }

}

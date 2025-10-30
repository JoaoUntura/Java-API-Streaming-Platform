# Query.getVideoReviews: VideoReviewConnection
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| input |  | Optional | CursorSearch |
| videoId |  | Optional | String |
            
## Example
```graphql
{
  getVideoReviews(input: {first : 123456789, after : 123456789}, videoId: "randomString") {
    videosReviews
    endCursor
    hasNextPage
  }
}

```
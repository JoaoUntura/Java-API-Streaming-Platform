# Query.courseById: Course
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| externalId |  | Optional | String |
            
## Example
```graphql
{
  courseById(externalId: "randomString") {
    externalId
    title
    thumbNailId
    videoList
  }
}

```